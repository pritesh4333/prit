<?php

	//	echo $filename;
	$myfile = fopen('TextFiles/calllog.text', "r") or die("Unable to open file!");
 	$data = fgets($myfile);
	fclose($myfile);
	echo $data;
	 

 
?>