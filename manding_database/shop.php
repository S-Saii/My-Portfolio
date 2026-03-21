<?php
session_start();
include 'db_config.php';

if (!isset($_SESSION['user_id']) || strtolower($_SESSION['role']) === 'admin') {
    header("Location: index.php");
    exit();
}

if (isset($_POST['buy_now'])) {
    $item_id = intval($_POST['item_id']);
    $qty = intval($_POST['quantity']);
    $branch = mysqli_real_escape_string($conn, $_POST['branch']);
    $method = mysqli_real_escape_string($conn, $_POST['payment_method']);
    
    $branch_col = strtolower($branch) . "_stock";

    $res = $conn->query("SELECT item_name, price, $branch_col FROM inventory WHERE id = $item_id");
    $item = $res->fetch_assoc();
    $total = $qty * $item['price'];

    if ($item[$branch_col] >= $qty) {
        if ($method === 'Cash') {
            $conn->query("UPDATE inventory SET $branch_col = $branch_col - $qty WHERE id = $item_id");
            $conn->query("INSERT INTO sales (item_id, quantity_sold, total_price, payment_method, branch_location) 
                          VALUES ($item_id, $qty, $total, 'Cash', '$branch')");
            echo "<script>alert('Order Placed at $branch! Total: ₱$total'); window.location.href='shop.php';</script>";
            exit();
        } else {
            $_SESSION['temp_order'] = [
                'item_id' => $item_id, 
                'qty' => $qty, 
                'total' => $total, 
                'branch' => $branch, 
                'branch_col' => $branch_col
            ];
            header("Location: process_payment.php");
            exit();
        }
    } else {
        echo "<script>alert('Sorry, $branch is out of stock!');</script>";
    }
}
$items = $conn->query("SELECT * FROM inventory");
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Shop - Manding Suman</title>
    <style>
        body { font-family: 'Segoe UI', sans-serif; background: #fefae0; margin: 0; padding: 40px 20px; color: #283618; }
        .container { max-width: 600px; margin: auto; }
        
        .header-bar { 
            background: white; padding: 20px 30px; border-radius: 15px; 
            display: flex; justify-content: space-between; align-items: center;
            margin-bottom: 30px; box-shadow: 0 4px 15px rgba(0,0,0,0.05);
        }
        .logout-link { color: #bc6c25; text-decoration: none; font-weight: bold; }
        .feedback-btn { background: #606c38; color: white; text-decoration: none; padding: 8px 15px; border-radius: 20px; font-weight: bold; }

        .product-card { 
            background: white; padding: 35px; border-radius: 20px; 
            border: 1px solid #ccd5ae; margin-bottom: 30px; 
            box-shadow: 0 8px 20px rgba(0,0,0,0.08); text-align: center;
        }
        h2 { color: #606c38; margin-top: 0; }
        .price-tag { font-size: 1.4em; color: #bc6c25; font-weight: bold; margin-bottom: 20px; }

        label { display: block; text-align: left; font-size: 0.9em; margin-top: 15px; font-weight: bold; color: #606c38; }
        input, select { 
            width: 100%; padding: 12px; margin-top: 5px; 
            border-radius: 8px; border: 1px solid #ddd; 
            box-sizing: border-box; font-size: 16px;
        }
        
        .order-btn { 
            background: #606c38; color: white; border: none; padding: 15px; 
            width: 100%; border-radius: 8px; font-weight: bold; cursor: pointer; 
            margin-top: 25px; font-size: 17px; transition: 0.3s;
        }
        .order-btn:hover { background: #283618; }
    </style>
</head>
<body>

    <div class="container">
        <div class="header-bar">
            <div>
                <h3 style="margin:0;">Hi, <?php echo htmlspecialchars($_SESSION['username']); ?>!</h3>
                <a href="feedback.php" class="feedback-btn" style="font-size: 0.8em; margin-top:5px; display:inline-block;">Give Feedback ⭐</a>
            </div>
            <a href="logout.php" class="logout-link">Logout</a>
        </div>

<?php while($row = $items->fetch_assoc()): ?>
<div class="product-card">
    <h2 style="margin-bottom: 15px;"><?php echo $row['item_name']; ?></h2>

    <?php 
      $food_pic = "logo.jpg";
      if($row['item_name'] == "Special Suman Latik") { $food_pic = "latik.jpg"; }
      if($row['item_name'] == "Suman sa Lihiya")     { $food_pic = "lihiya.jpg"; }
      if($row['item_name'] == "Cassava Cake")        { $food_pic = "cassava.jpg"; }
    ?>
    <img src="<?php echo $food_pic; ?>" alt="Product Image" 
         style="width: 200px; height: 150px; object-fit: cover; border-radius: 10px; display: block; margin: 0 auto 15px auto;">

    <div class="price-tag">₱<?php echo number_format($row['price'], 2); ?></div>
    
    <form method="POST">
        <input type="hidden" name="item_id" value="<?php echo $row['id']; ?>">
        
        <label>Quantity</label>
        <input type="number" name="quantity" value="1" min="1" required 
               style="width: 100%; box-sizing: border-box;">

        <label>Location</label>
        <select name="branch" required style="width: 100%; box-sizing: border-box;">
            <option value="Commonwealth">Commonwealth (Stock: <?php echo $row['commonwealth_stock']; ?>)</option>
            <option value="Katipunan">Katipunan (Stock: <?php echo $row['katipunan_stock']; ?>)</option>
            <option value="Malingap">Malingap (Stock: <?php echo $row['malingap_stock']; ?>)</option>
        </select>

        <label>Payment Method</label>
        <select name="payment_method" style="width: 100%; box-sizing: border-box;">
            <option value="Cash">Cash on Delivery</option>
            <option value="GCash">GCash</option>
        </select>
        
        <button type="submit" name="buy_now" class="order-btn">Order Now</button>
    </form>
</div>
<?php endwhile; ?>
    </div>

</body>
</html>