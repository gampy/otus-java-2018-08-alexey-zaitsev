<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <title>Cache output</title>
</head>
<body>

<div class="header">
    <b>All cached DB users:</b><br/>
</div>

<div class="maintext">
    <#list users as key, value>
      <p> ${key}: ${value}
    </#list>
</div>

<div class="footer">
</div>

</body>
</html>