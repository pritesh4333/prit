<?php
 
$json_str = file_get_contents('php://input');
 
  

$str = str_replace('\\', '', $json_str);

 
 
	$myfiles = fopen("Image.txt", "a") or die("Unable to open file!"); 
	fwrite($myfiles, $str);
	fclose($myfiles);
	 
 
 
 
echo '{"status":"Y"}';



?>
