<?php
 
$json_str = file_get_contents('php://input');
 
  

 

 
 
	$myfiles = fopen("crash.txt", "a") or die("Unable to open file!"); 
	fwrite($myfiles, $json_str);
	fclose($myfiles);
	 
 
 
 
echo '{"status":"Y"}';



?>
