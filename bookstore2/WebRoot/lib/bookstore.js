function getBooks(page, size) {
    $.ajax({
        url: "./books?page=" + page + "&size=" + size,
        type: "GET",
        success: function(data, status) {
            showBooks(data, status);
            createPager(data);
        },
        error: function(xhr, status, error) {
            console.error("ajax failed:" + error);
        }
    });
}

function showBooks(data, status) {
    var container = $("#bookTableContainer");
    // 清除之前的内容
    container.empty();

    var books = data.books;
    for (var i=0; i<books.length; i++) {
        var book = books[i];
        console.log(book);
        var dd = $("<div class='col-12 col-md-6 col-lg-4 res-margin'></div>");
        var d = $("<div class='single-promo color-1 bg-hover hover-bottom text-center p-5'></div>");
        d.append("<img src='./upload/" + book.picture + "'></img>").append("<br><br>");
        d.append("书名：" + book.name).append("<br>");
        d.append("作者：" + book.author).append("<br>");
        d.append("价格：" + book.price).append("<br>");
        d.append("发行：" + book.publish).append("<br>");
        dd.append(d);
        container.append(dd);
    }
}

function createPager(data) {
    var container = $("#bookPagerContainer");
    var pages = data.pages;
    var page = data.page;
    var size = data.size;

    // 清除之前的内容
    container.empty();

    if (page != 1) {
        container.append("<span page='" + (page -1) +"' size='" + size + "'> <<上一页 </span>&nbsp;&nbsp;&nbsp;&nbsp;")
    }

    for (var i=1; i<=pages; i++) {
        container.append("<span page='" + i+ "' size='" + size + "'> " + i + " </span>&nbsp;&nbsp;");
    }

    if (page != pages) {
        container.append("&nbsp;&nbsp;&nbsp;&nbsp;<span page='" + (page + 1)+ "' size='" + size + "'> 下一页>> </span>")
    }

    container.find("span").each(function(index, e) {
        $(e).click(function() {
            var p = $(this).attr("page");
            var s = $(this).attr("size");
            getBooks(p, s);
        });
    });
}