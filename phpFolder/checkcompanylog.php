<?php
	include_once './db_functions.php';
	
	//Create Object for DB_Functions class and get JSON posted by Android Application
	$db = new DB_Functions();
	$json = $_POST["companyLogJSON"];
	
	//Decode JSON into an Array
	if (get_magic_quotes_gpc()) { $json = stripslashes($json); }
	$data = json_decode($json);
	
	//Util arrays to create response JSON
	$a=array();
	$b=array();
	
	$result = $db->checkCompanyLog($data[0]->company_name, $data[0]->password);
	if(mysql_num_rows($result) > 0) {
		$b["log"] = 'yes';
		
		$company = mysql_fetch_array($result);
		$b["id"] = $company["id"];
		$b["name"] = $company["name"];
		$b["logo"] = $company["logo"];
		$b["card"] = $company["card"];
		$b["up_date"] = $company["up_date"];
	} else {
		$b["log"] = 'no';
	}
	
	array_push($a,$b);
	echo json_encode($a);
?>