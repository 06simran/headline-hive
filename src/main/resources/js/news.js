document.addEventListener("DOMContentLoaded", () => {
    const articlesDiv = document.getElementById("articles");
    const lastUpdated = document.getElementById("last-updated");

    function fetchArticles(category = "") {
        articlesDiv.innerHTML = "Loading articles…";
        let url = "/api/news";
        if (category && category !== "general") {
            url += "/category/" + category;
        }

        fetch(url)
            .then(res => res.json())
            .then(data => {
                if (!data.articles || data.articles.length === 0) {
                    articlesDiv.innerHTML = "No articles found.";
                    return;
                }

                articlesDiv.innerHTML = data.articles.map(article => `
                    <div class="article">
                        <h3>${article.title}</h3>
                        <p>${article.description || ""}</p>
                        <a href="${article.url}" target="_blank">Read more</a>
                    </div>
                `).join("");

                const now = new Date();
                lastUpdated.textContent = "Last updated: " + now.toLocaleTimeString();
            })
            .catch(() => {
                articlesDiv.innerHTML = "Failed to load articles.";
            });
    }

    // Initial load
    fetchArticles();

    // Category link click
    document.querySelectorAll(".category-link").forEach(link => {
        link.addEventListener("click", e => {
            e.preventDefault();
            const category = e.target.getAttribute("data-category");
            fetchArticles(category);
        });
    });
});
