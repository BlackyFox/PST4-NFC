<html>
	<head>
		<title>View People</title>
		<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
		<style>
			body { font: normal medium/1.4 sans-serif; }
			table {
				border-collapse: collapse;
				width: 20%;
				margin-left: auto;
				margin-right: auto;
			}
			tr > td {
				padding: 0.25rem;
				text-align: center;
				border: 1px solid #ccc;
			}
			tr:nth-child(even) { background: #FAE1EE; }
			tr:nth-child(odd) { background: #edd3ff; }
			tr#header{ background: #c1e2ff; }
			div.header{
				padding: 10px;
				background: #e0ffc1;
				width:30%;
				color: #008000;
				margin:5px;
			}
			div.refresh{
				margin-top:10px;
				width: 5%;
				margin-left: auto;
				margin-right: auto;
			}
			div#norecord{
				margin-top:10px;
				width: 15%;
				margin-left: auto;
				margin-right: auto;
			}
		</style>
		
		<script>
			function refreshPage() { location.reload(); }
		</script>
	</head>
	
	<body>
		<center>
			<div class="header">
				Android SQLite and MySQL Sync Results
			</div>
		</center>
		
		<?php
			include_once 'db_functions.php';
			$db = new DB_Functions();
			$people = $db->getAllPeople();
			if ($people != false)
				$no_of_people = mysql_num_rows($people);
			else
				$no_of_people = 0;
		?>
		
		<?php if ($no_of_people > 0) { ?>
		
		<table>
			<tr id="header"><td>Id</td>
							<td>Username</td>
							<td>Password</td>
							<td>Name</td>
							<td>First-name</td>
							<td>Sexe</td>
							<td>Date of birth</td>
							<td>Mail</td>
							<td>City</td>
							<td>Update</td></tr>
			
			<?php while ($row = mysql_fetch_array($people)) { ?> 
			
			<tr><td><span><?php echo $row["id"] ?></span></td>
				<td><span><?php echo $row["username"] ?></span></td>
				<td><span><?php echo $row["password"] ?></span></td>
				<td><span><?php echo $row["name"] ?></span></td>
				<td><span><?php echo $row["first_name"] ?></span></td>
				<td><span><?php echo $row["sexe"] ?></span></td>
				<td><span><?php echo $row["date_of_birth"] ?></span></td>
				<td><span><?php echo $row["mail"] ?></span></td>
				<td><span><?php echo $row["city"] ?></span></td>
				<td><span><?php echo $row["up_date"] ?></span></td></tr>
			
			<?php } ?>
			
		</table>
		
		<?php } else { ?>
		
		<div id="norecord">
			No records in MySQL DB
		</div>
		
		<?php } ?>
		
		<div class="refresh">
			<button onclick="refreshPage()">Refresh</button>
		</div>
		
	</body>
</html>