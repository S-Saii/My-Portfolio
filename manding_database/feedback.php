<?php
session_start();
include 'db_config.php';

if (!isset($_SESSION['user_id'])) {
    header("Location: index.php");
    exit();
}

if (isset($_POST['submit_feedback'])) {
    $name = mysqli_real_escape_string($conn, $_POST['customer_name']);
    $branch = mysqli_real_escape_string($conn, $_POST['branch']);
    $rating = intval($_POST['rating']);
    $experience = mysqli_real_escape_string($conn, $_POST['experience']);

    $sql = "INSERT INTO feedback (customer_name, branch, rating, experience) 
            VALUES ('$name', '$branch', $rating, '$experience')";

    if ($conn->query($sql)) {
        echo "<script>alert('Thank you for your feedback!'); window.location.href='shop.php';</script>";
        exit();
    }
}
?>
<!DOCTYPE html>
<html>
<head>
    <title>Customer Feedback</title>
    <style>
        body { font-family: sans-serif; background: #fefae0; display: flex; align-items: center; justify-content: center; height: 100vh; margin: 0; }
        .card { background: white; padding: 40px; border-radius: 15px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); width: 400px; text-align: center; }
        h2 { margin-bottom: 20px; }
        input, select, textarea { width: 100%; padding: 12px; margin: 10px 0; border: 1px solid #ddd; border-radius: 8px; box-sizing: border-box; font-family: sans-serif; }
        textarea { height: 100px; resize: none; }
        .submit-btn { background: #606c38; color: white; border: none; padding: 12px; width: 100%; border-radius: 8px; cursor: pointer; font-weight: bold; font-size: 16px; }
        .cancel-link { display: block; margin-top: 15px; color: #bc6c25; text-decoration: none; font-weight: bold; }
    </style>
</head>
<body>
    <div class="card">
        <h2>Customer Feedback</h2>
        <form method="POST">
            <input type="text" name="customer_name" placeholder="Your Name" value="<?php echo $_SESSION['username']; ?>" required>
            
            <select name="branch" required>
                <option value="" disabled selected>Which branch did you visit/ordered from?</option>
                <option value="Commonwealth">Commonwealth</option>
                <option value="Katipunan">Katipunan</option>
                <option value="Malingap">Malingap</option>
            </select>

            <select name="rating" required>
                <option value="" disabled selected>Rate us (1-5 Stars)</option>
                <option value="5">5 - Excellent</option>
                <option value="4">4 - Very Good</option>
                <option value="3">3 - Good</option>
                <option value="2">2 - Fair</option>
                <option value="1">1 - Poor</option>
            </select>

            <textarea name="experience" placeholder="How was your experience?" required></textarea>
            
            <button type="submit" name="submit_feedback" class="submit-btn">Submit Feedback</button>
        </form>
        <a href="shop.php" class="cancel-link">← Cancel</a>
    </div>
</body>
</html>