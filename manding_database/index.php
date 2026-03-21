<?php
session_start();
include 'db_config.php'; //

if (isset($_POST['login'])) {
    $user = mysqli_real_escape_string($conn, $_POST['username']);
    $pass = mysqli_real_escape_string($conn, $_POST['password']);

    $sql = "SELECT * FROM users WHERE username='$user' AND password='$pass'";
    $result = $conn->query($sql);

    if ($result && $result->num_rows > 0) {
        $row = $result->fetch_assoc();
        $_SESSION['user_id'] = $row['id'];
        $_SESSION['username'] = $row['username'];
        $_SESSION['role'] = $row['role']; 

        if (strtolower($row['role']) === 'admin') {
            header("Location: admin_dashboard.php");
        } else {
            header("Location: shop.php");
        }
        exit();
    } else {
        echo "<script>alert('Invalid Credentials!');</script>";
    }
}
?>
<!DOCTYPE html>
<html>
<head>
    <title>Manding Suman Latik! - Welcome</title>
    <style>
        body { font-family: sans-serif; background: #fefae0; display: flex; align-items: center; justify-content: center; height: 100vh; margin: 0; }
        .card { background: white; padding: 40px; border-radius: 15px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); width: 320px; text-align: center; }
        h2 { color: #606c38; margin-bottom: 25px; }
        input { width: 100%; padding: 12px; margin: 10px 0; border: 1px solid #ddd; border-radius: 8px; box-sizing: border-box; }
        button { width: 100%; padding: 12px; background: #606c38; color: white; border: none; border-radius: 8px; cursor: pointer; font-weight: bold; margin-top: 10px; }
        .footer-links { margin-top: 20px; font-size: 0.9em; }
        .footer-links a { color: #bc6c25; text-decoration: none; font-weight: bold; }
    </style>
</head>
<body>
    <div class="card" style="text-align: center;">
    <img src="logo.jpg" alt="Manding Logo" style="width: 150px; height: auto; margin-bottom: 20px; border-radius: 50%;">
    
    <h2>Welcome to Manding Suman Latik!</h2>
    <form method="POST">
        <input type="text" name="username" placeholder="Username" required>
        <input type="password" name="password" placeholder="Password" required>
        <button type="submit" name="login">Login</button>
    </form>
    <div class="footer-links">
        Don't have an account? <a href="register.php">Sign Up</a>
    </div>
</div>
</body>
</html>