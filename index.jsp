<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>GloVe Vector Lookup</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f0f0f0;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        margin: 0;
    }
    .container {
        background-color: #fff;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }
    h1 {
        text-align: center;
        color: #333;
    }
    form {
        display: flex;
        flex-direction: column;
        align-items: center;
    }
	label {
        font-weight: bold;
        font-family: 'Verdana', sans-serif;
        margin-bottom: 10px;
    }
    input[type="text"] {
        width: 100%;
        padding: 10px;
        margin-bottom: 10px;
        border: 1px solid #ccc;
        border-radius: 4px;
    }
    button {
        padding: 10px 20px;
        background-color: #007BFF;
        border: none;
        border-radius: 4px;
        color: #fff;
        cursor: pointer;
    }
    button:hover {
        background-color: #0056b3;
    }
    
</style>
</head>
<body>
    <div class="container">
        <h1>Text into Vector Data</h1>
        <form action="GloveVectorServlet1" method="post">
            <label for="text">Enter your text here:</label>
            <input type="text" id="text" name="text" required>
            <button type="submit">Submit</button>
        </form>
    </div>
</body>
</html>
