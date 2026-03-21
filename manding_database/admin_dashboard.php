<?php
session_start();
include 'db_config.php';

if (!isset($_SESSION['role']) || strtolower($_SESSION['role']) !== 'admin') {
    header("Location: index.php");
    exit();
}

if (isset($_POST['update_all_branches'])) {
    $item_id = intval($_POST['item_id']);
    $k_qty = intval($_POST['k_qty']);
    $c_qty = intval($_POST['c_qty']);
    $m_qty = intval($_POST['m_qty']);
    $conn->query("UPDATE inventory SET katipunan_stock=$k_qty, commonwealth_stock=$c_qty, malingap_stock=$m_qty WHERE id=$item_id");
}

if (isset($_GET['delete_user'])) {
    $user_id = intval($_GET['delete_user']);
    $conn->query("DELETE FROM users WHERE id = $user_id AND role != 'Admin'");
    header("Location: admin_dashboard.php");
    exit();
}

$inventory = $conn->query("SELECT * FROM inventory");
$sales = $conn->query("SELECT s.*, i.item_name FROM sales s JOIN inventory i ON s.item_id = i.id ORDER BY s.sale_date DESC");
$feedback = $conn->query("SELECT * FROM feedback ORDER BY submission_date DESC");
$guests = $conn->query("SELECT id, username FROM users WHERE role = 'Guest'");
?>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard - Manding Suman</title>
    <style>
        body { font-family: 'Segoe UI', sans-serif; background: #fefae0; margin: 0; padding: 40px 20px; color: #283618; }
        .container { max-width: 900px; margin: auto; background: white; padding: 40px; border-radius: 20px; box-shadow: 0 10px 30px rgba(0,0,0,0.1); }
        
        .header { display: flex; justify-content: space-between; align-items: center; border-bottom: 3px solid #606c38; margin-bottom: 40px; padding-bottom: 10px; }
        .logout-btn { color: #bc6c25; text-decoration: none; font-weight: bold; font-size: 1.1em; }
        
        section { margin-bottom: 60px; }
        h3 { color: #606c38; border-left: 5px solid #606c38; padding-left: 15px; margin-bottom: 20px; }
        
        table { width: 100%; border-collapse: collapse; background: #fff; margin-top: 10px; }
        th, td { padding: 15px; border: 1px solid #ddd; text-align: left; }
        th { background: #606c38; color: white; }
        
        input[type="number"], .save-btn { width: 100%; padding: 10px; box-sizing: border-box; border-radius: 5px; border: 1px solid #ccc; }
        .save-btn { background: #606c38; color: white; border: none; font-weight: bold; cursor: pointer; margin-top: 5px; }
        .save-btn:hover { background: #283618; }
        
        .delete-link { color: #e63946; text-decoration: none; font-weight: bold; }
        .rating { color: #bc6c25; font-weight: bold; }
    </style>
</head>
<body>

    <div class="container">
        <div class="header">
            <h1 style="margin:0;">Admin Dashboard</h1>
            <a href="logout.php" class="logout-btn">Logout</a>
        </div>

        <section>
            <h3>Branch Inventory Monitoring</h3>
            <table>
                <tr>
                    <th>Item</th>
                    <th style="width:100px;">Katipunan</th>
                    <th style="width:100px;">Commonwealth</th>
                    <th style="width:100px;">Malingap</th>
                    <th style="width:120px;">Action</th>
                </tr>
                <?php while($item = $inventory->fetch_assoc()): ?>
                <tr>
                    <form method="POST">
                        <input type="hidden" name="item_id" value="<?php echo $item['id']; ?>">
                        <td><strong><?php echo $item['item_name']; ?></strong></td>
                        <td><input type="number" name="k_qty" value="<?php echo $item['katipunan_stock']; ?>"></td>
                        <td><input type="number" name="c_qty" value="<?php echo $item['commonwealth_stock']; ?>"></td>
                        <td><input type="number" name="m_qty" value="<?php echo $item['malingap_stock']; ?>"></td>
                        <td><button type="submit" name="update_all_branches" class="save-btn">Update</button></td>
                    </form>
                </tr>
                <?php endwhile; ?>
            </table>
        </section>

        <section>
            <h3>Transaction Logs</h3>
            <div style="overflow-x:auto;">
                <table>
                    <tr><th>Date</th><th>Item</th><th>Qty</th><th>Total</th><th>Branch</th><th>Method</th></tr>
                    <?php while($sale = $sales->fetch_assoc()): ?>
                    <tr>
                        <td><small><?php echo $sale['sale_date']; ?></small></td>
                        <td><?php echo $sale['item_name']; ?></td>
                        <td><?php echo $sale['quantity_sold']; ?></td>
                        <td>₱<?php echo number_format($sale['total_price'], 2); ?></td>
                        <td><strong><?php echo $sale['branch_location'] ?? 'N/A'; ?></strong></td>
                        <td><?php echo $sale['payment_method']; ?></td>
                    </tr>
                    <?php endwhile; ?>
                </table>
            </div>
        </section>

        <section>
            <h3>Customer Feedback</h3>
            <table>
                <tr><th>Customer</th><th>Branch</th><th>Rating</th><th>Experience</th></tr>
                <?php if($feedback->num_rows > 0): ?>
                    <?php while($f = $feedback->fetch_assoc()): ?>
                    <tr>
                        <td><?php echo htmlspecialchars($f['customer_name']); ?></td>
                        <td><?php echo htmlspecialchars($f['branch']); ?></td>
                        <td class="rating"><?php echo $f['rating']; ?>/5 ⭐</td>
                        <td><?php echo htmlspecialchars($f['experience']); ?></td>
                    </tr>
                    <?php endwhile; ?>
                <?php else: ?>
                    <tr><td colspan="4" style="text-align:center;">No feedback yet.</td></tr>
                <?php endif; ?>
            </table>
        </section>

        <section>
            <h3>User Management (Guests)</h3>
            <table>
                <tr><th>User ID</th><th>Username</th><th>Action</th></tr>
                <?php while($guest = $guests->fetch_assoc()): ?>
                <tr>
                    <td>#<?php echo $guest['id']; ?></td>
                    <td><?php echo htmlspecialchars($guest['username']); ?></td>
                    <td><a href="admin_dashboard.php?delete_user=<?php echo $guest['id']; ?>" class="delete-link" onclick="return confirm('Remove this user permanently?')">Remove User</a></td>
                </tr>
                <?php endwhile; ?>
            </table>
        </section>

    </div>
</body>
</html>