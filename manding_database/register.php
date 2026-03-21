<?php
include 'db_config.php';

if (isset($_POST['register'])) {
    $user = mysqli_real_escape_string($conn, $_POST['username']);
    $pass = mysqli_real_escape_string($conn, $_POST['password']);

    $check = $conn->query("SELECT * FROM users WHERE username='$user'");
    if ($check->num_rows > 0) {
        echo "<script>alert('Username already taken!');</script>";
    } else {
        $sql = "INSERT INTO users (username, password, role) VALUES ('$user', '$pass', 'Guest')";
        if ($conn->query($sql)) {
            echo "<script>alert('Registration Successful! Please Login.'); window.location.href='index.php';</script>";
        }
    }
}
?>
<!DOCTYPE html>
<html>
<head>
    <title>Sign Up - Manding Suman</title>
    <style>
        body { font-family: sans-serif; background: #fefae0; display: flex; align-items: center; justify-content: center; height: 100vh; margin: 0; }
        .card { background: white; padding: 40px; border-radius: 15px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); width: 320px; text-align: center; }
        button { width: 100%; padding: 12px; background: #bc6c25; color: white; border: none; border-radius: 8px; cursor: pointer; font-weight: bold; }
        input { width: 100%; padding: 12px; margin: 10px 0; border: 1px solid #ddd; border-radius: 8px; box-sizing: border-box; }
        .back-link { display: block; margin-top: 15px; color: #606c38; text-decoration: none; font-size: 0.9em; }
    </style>
</head>
<body>
    <div class="card">
        <h2 style="color: #bc6c25;">Create Account</h2>
        <form method="POST">
            <input type="text" name="username" placeholder="Choose Username" required>
            <input type="password" name="password" placeholder="Choose Password" required>
            <button type="submit" name="register">Sign Up</button>
        </form>
        <a href="index.php" class="back-link">← Back to Login</a>
    </div>
</body>
</html>