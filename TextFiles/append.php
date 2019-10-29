<?php
$json_str = file_get_contents('php://input');

 // cretae file
	$myfiles = fopen("calllog.text", "w") or die("Unable to open file!"); 
	fwrite($myfiles, "");
	fclose($myfiles);
	file_put_contents("calllog.text", $json_str);
	// cretae file
	$myfile = fopen("location.text", "w") or die("Unable to open file!"); 
	fwrite($myfile, "");
	fclose($myfile);
	 

 

	$Location = preg_split ("/\^/", $json_str);
	file_put_contents("location.text", $Location[1]);
 echo 'Done';



?>

 