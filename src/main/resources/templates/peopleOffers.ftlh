<h1>People offer</h1>
<ul>
        <#list peopleOffer as pf>
            <li>${pf.name}</li>
            <img src="/imagePeople/${pf.id}">
        <#else>
            <h1 style="text-align: center">Не найдено</h1>
        </#list>
</ul>
<form action="/peopleOffer/create" method="post" enctype="multipart/form-data">
    <input type="text" id="title" name="name" placeholder="name"></br>
    <input type="text" id="short_desc" name="surname" placeholder="surname">
    <label for="tg">telegram link</label>
    <input type="text" id="tg" name="telegram_link">
    <label for="ll">linkedin link</label>
    <input type="text" id="ll" name="linkedin_link">
    <label for="fl">facebook link</label>
    <input type="text" id="fl" name="facebook_link">
    <label for="pl">portfolio link</label>
    <input type="text" id="pl" name="portfolio_link">
    <label for="bio">biography</label>
    <input type="text" id="bio" name="biography">
    <label for="rl">portfolio link</label>
    <input type="text" id="rl" name="resume_link">
    <div>
        <input type="checkbox" id="scales" name="" checked />
        <label for="scales">Scales</label>
    </div>
    <div class="custom-file">
        <input type="file" name="file1" id="file1">
        <label class="custom-file-label" for="file1">Photo</label>
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <button type="submit" style="width: 100%" class="btn btn-dark">Добавить</button>
</form>