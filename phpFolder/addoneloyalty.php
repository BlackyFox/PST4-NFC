<?php
	include_once './db_functions.php';
	
	//Create Object for DB_Functions class and get JSON posted by Android Application
	$db = new DB_Functions();
	$json = $_POST["addOneLoyaltyJSON"];
	
	//Decode JSON into an Array
	if (get_magic_quotes_gpc()) { $json = stripslashes($json); }
	$data = json_decode($json);
	
	//Util arrays to create response JSON
	$a=array();
	$b=array();
	
	if($db->checkNumClientWithIdComp($data[0]->id_company, $data[0]->num_client)) {
		$b["goodCompany"] = 'yes';
		
		if($db->addOneLoyalty($data[0]->num_client, $data[0]->up_date)) {
			$b["works"] = 'yes';
			$res = $db->getClientWithNumClient($data[0]->num_client);
			$row = mysql_fetch_array($res);
			$b["nb_loyalties"] = $row["nb_loyalties"];
		} else {
			$b["works"] = 'no';
		}
	} else {
		$b["goodCompany"] = 'no';
	}
	
	array_push($a,$b);
	echo json_encode($a);
?>