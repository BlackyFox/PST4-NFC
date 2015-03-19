<?php
	include_once './db_functions.php';
	
	//Create Object for DB_Functions class and get JSON posted by Android Application
	$db = new DB_Functions();
	$json = $_POST["addClientManuallyJSON"];
	
	//Decode JSON into an Array
	if (get_magic_quotes_gpc()) { $json = stripslashes($json); }
	$data = json_decode($json);
	
	//Util arrays to create response JSON
	$a=array();
	$b=array();
	
	
	if($db->checkIfClientIsOk($data[0]->num_client, $data[0]->name, $data[0]->first_name, $data[0]->date_of_birth, $data[0]->sexe)) {
		$b["dataOk"] = 'yes';
		
		if($db->checkIfTruePeople($data[0]->connected_people, $data[0]->num_client)) {
			$b["truePeople"] = 'yes';
			
			$res = $db->getClientWithNumClient($data[0]->num_client);
			$row = mysql_fetch_array($res);
			$db->updateClient($row["id_peop"], $row["id"]);
			$res = $db->getClientWithNumClient($data[0]->num_client);
			$row = mysql_fetch_array($res);
			$b["id"] = $row["id"];
			$b["id_peop"] = $row["id_peop"];
			$b["id_comp"] = $row["id_comp"];
			$b["num_client"] = $row["num_client"];
			$b["nb_loyalties"] = $row["nb_loyalties"];
			$b["last_used"] = $row["last_used"];
			$b["up_date"] = $row["up_date"];
			
			$res = $db->getCompanyWithId($b["id_comp"]);
			$row = mysql_fetch_array($res);
			$b["company_id"] = $row["id"];
			$b["company_name"] = $row["name"];
			$b["company_logo"] = $row["logo"];
			$b["company_card"] = $row["card"];
			$b["company_up_date"] = $row["up_date"];
		} else {
			$b["truePeople"] = 'no';
		}
	} else {
		$b["dataOk"] = 'no';
	}
	
	array_push($a,$b);
	
	echo json_encode($a);
?>