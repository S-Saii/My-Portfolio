<?php
session_start();
include 'db_config.php';

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
        echo "<script>alert('Invalid Username or Password!'); window.location.href='login.php';</script>";
    }
}
?>
<!DOCTYPE html>
<html>
<head>
    <title>Login - Manding Suman</title>
    <style>
        body { font-family: sans-serif; background: #fefae0; display: flex; align-items: center; justify-content: center; height: 100vh; margin: 0; }
        .back-btn { position: absolute; top: 20px; left: 20px; text-decoration: none; color: #606c38; border: 1px solid #606c38; padding: 7px 18px; border-radius: 25px; background: white; font-weight: bold; }
        .card { background: white; padding: 40px; border-radius: 15px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); width: 320px; text-align: center; }
        input { width: 100%; padding: 12px; margin: 10px 0; border: 1px solid #ddd; border-radius: 8px; box-sizing: border-box; }
        button { width: 100%; padding: 12px; background: #606c38; color: white; border: none; border-radius: 8px; cursor: pointer; font-weight: bold; width: 100%; }
    </style>
</head>
<body>
    <a href="index.php" class="back-btn">← Back to Home</a>
    <div class="card">
        <h2>Manding Suman Login</h2>
        <form method="POST">
            <input type="text" name="username" placeholder="Username" required>
            <input type="password" name="password" placeholder="Password" required>
            <button type="submit" name="login">Login</button>
        </form>
    </div>
</body>
</html>