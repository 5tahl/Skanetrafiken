<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Search</title>
<link rel="stylesheet" href="style.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>

    
    <div class="container light-mode"> 
    
	   <div class="search-div">
		   <div class="search-text">Vart vill du ta bussen?</div>
		
		   <form action="Controller" method="get" class="search-form">
		   
		        <p class="radio-style">Yes or no to cookies? Can't make theme dark without cookies.</p>
		        <input type="radio" id="yes" name="okCookie" value="yes" required>
                <label for="light" class="radio-style">Yes</label>
                <input type="radio" id="no" name="okCookie" value="no">
                <label for="dark" class="radio-style">No</label><br>
				
			    <input type="radio" id="light" name="theme" value="light" required>
				<label for="light" class="radio-style">Light Theme</label>
				<input type="radio" id="dark" name="theme" value="dark">
				<label for="dark" class="radio-style">Dark Theme</label><br>
  
				<input type="text" name="search" placeholder="Search Station"
					class="search-field" />
				<button type="submit" class="search-btn">
					<i class="fa fa-search"></i>
				</button>
			</form>
		</div>
	</div>
</body>
</html>