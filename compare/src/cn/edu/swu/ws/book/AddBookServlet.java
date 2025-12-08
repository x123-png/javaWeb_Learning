package cn.edu.swu.ws.book;


import cn.edu.swu.ws.repo.DatabaseService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload2.core.DiskFileItem;
import org.apache.commons.fileupload2.core.DiskFileItemFactory;
import org.apache.commons.fileupload2.core.FileItem;
import org.apache.commons.fileupload2.jakarta.servlet5.JakartaServletDiskFileUpload;
import org.apache.commons.fileupload2.jakarta.servlet5.JakartaServletFileUpload;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/admin/addBook")
public class AddBookServlet extends HttpServlet {

    // 上传文件存储目录
    private static final String UPLOAD_DIRECTORY = "upload";

    // 上传配置
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 检测是否为多媒体上传
        if (!JakartaServletFileUpload.isMultipartContent(request)) {
            // 如果不是则停止
            PrintWriter writer = response.getWriter();
            writer.println("Error: 表单必须包含 enctype=multipart/form-data");
            writer.flush();
            return;
        }

        // 配置上传参数
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("jakarta.servlet.context.tempdir");
        DiskFileItemFactory factory = DiskFileItemFactory.builder().setFile(repository).get();
        JakartaServletDiskFileUpload upload = new JakartaServletDiskFileUpload(factory);
        // 设置最大文件上传值
        upload.setFileSizeMax(MAX_FILE_SIZE);
        // 设置最大请求值 (包含文件和表单数据)
        upload.setSizeMax(MAX_REQUEST_SIZE);

        // 构造临时路径来存储上传的文件
        // 这个路径相对当前应用的目录
        String uploadPath = Paths.get(request.getServletContext().getRealPath("./"), UPLOAD_DIRECTORY).toString();
        System.out.println(uploadPath);
        // 如果目录不存在则创建
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        try {
            // 解析请求的内容提取文件数据
            String name = null, author = null, memo = null, publish = null, picture = null;
            double price = 0;

            List<DiskFileItem> items = upload.parseRequest(request);
            for (FileItem item : items) {
                if (item.isFormField()) {
                    String filedName = item.getFieldName();
                    if (filedName.equals("name")) {
                        name = item.getString();
                    } else if(filedName.equals("author")) {
                        author = item.getString();
                    } else if(filedName.equals("price")) {
                        price = Double.parseDouble(item.getString());
                    } else if(filedName.equals("publish")) {
                        publish = item.getString();
                    } else if(filedName.equals("memo")) {
                        memo = item.getString();
                    }
                 } else {
                    String fileName = new File(item.getName()).getName();
                    Path filePath = Path.of(uploadPath, fileName);
                    System.out.println(filePath);
                    item.write(filePath); // 保存文件到硬盘
                    picture = fileName;
                }
            }
            this.saveToDb(request, name, author, price, memo, publish, picture);
        } catch (Exception ex) {
            throw new ServletException(ex);
        }

        response.sendRedirect("./books");
    }

    private void saveToDb(HttpServletRequest request,
        String name, String author, double price, String memo, String publish, String picture) throws SQLException {
        ServletContext context = this.getServletContext();
        DatabaseService dbService = (DatabaseService)context.getAttribute(DatabaseService.CONTEXT_KEY);
        String insertSQL = "insert into book (name, author, price, memo, publish, picture) " +
                "values ('%s', '%s', %s, '%s', '%s', '%s')";

        dbService.execute(String.format(insertSQL, name, author, price, memo, publish, picture));
    }

}
