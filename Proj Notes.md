Spring Boot’s defaults are fairly safe, so you have to deliberately opt out of protections to plant a bug. - Explain what the framework protects you from by default, and here’s what happens when you bypass it.

SpringBoot Notes 
	- **Use `JdbcTemplate` or plain JDBC, not JPA/Hibernate, for v1.** JPA parameterizes queries by default and hides the SQL, so it makes SQL injection harder to demonstrate and understand. Writing the SQL yourself is better for learning.
	- **Write a simple login yourself at first, and skip Spring Security.** That lets you create broken-auth bugs naturally. Then adding Spring Security later becomes your “hardened v2”, which is a nice story.
	- **Keep the dependencies minimal:** Spring Web, Thymeleaf, JDBC, and an embedded database (H2 or SQLite) so there’s nothing to install.
	- **Keep the app tiny.** A notes app with users, login, a search box, and a comments feature gives you places for all four vulnerabilities above. Nothing more.
- ### Tips for the skim test

- **Top of the README matters most.** The title, one-liner, warning, screenshot, and vulnerability table should all fit on the first screen.
- **The vulnerability table is your hook.** It shows engineers your scope and recruiters your security vocabulary at a glance.
- **Keep the “Status” column honest.** “Planned” and “In progress” are fine, and it means you can publish now and update as you go.
- **Leave OWASP and CWE fields as `TBD` until you’ve done the research,** then fill them in with your own findings.

bcrypt - password hashing function:
	Converts plaintext (passwords) into irreversible fixed-length strings. Even if the database is leaked, it ensures the password cannot be recovered. 
	bcrypt automatically generates and incorporates a unique 16-byte salt for every password so that identical passwords produce completely different hashes, rendering rainbow table attacks useless. 
		Rainbow tables compress passwords and their respective hashes by using hash chains. Once an attacker leaks a database of hashed passwords, they can crack them.
			An attacker first checks the endpoints - checking if stolen hashes match any of the final hashes stored in the rainbow table. 
			If no immediate match, attacker applies a table reduction function on the stolen hash then hashes the result and checks the table each time, stopping once the output matches a stored endpoint. 
			Once a match, the attacker takes the plaintext password paired with that endpoint and then takes the starting password and runs the entire length of the rainbow tables again. This is because the rainbow table deleted all the passwords in between the endpoints (to save space), the attacker has to generate that specific chain to see the real password inside it. 
	 bcrypt uses a configurable work factor (iteration count) that intentionally slows down the hashing process. The cost factor can be increased to keep the algorithm resistant to brute-force attacks.
		 During a brute force-attack, an attacker uses software to guess billions of passwords per second. If each guess takes a millisecond, it might not take too long. However, if each guess were to take even 0.1 seconds or God forbid 0.5 seconds then the attack could suddenly take thousands of years. 
	 Results in a 60-character output string. 
	PROS & CONS OF BCRYPT:
		PROS: 
			Adaptive - Scaling the work factor allows it to adapt to increasing hardware performance over time. 
			Self Contained - The salt and cost parameters are stored directly inside the resulting hash string, which helps keep database management more attainable. 
				Less code to maintain, fewer columns and simpler queries, meaning fewer bugs overall. 
				Easy to scale, old passwords with cost factor of 10 and new passwords with a cost factor of 12 can live side-by-side in the exact same column. 
			Supported Greatly - Major programming languages include bcrypt in their libraries and it is available natively. 
		CONS:
			CPU Intensive - It is designed to be slow so heavy concurrent hashing can consume significant CPU resources. 
			72-Byte Input Limit - The underlying Blowfish algorithm truncates password inputs after 72 bytes. 
			OWASP recommendations - Newer algorithms like Argon2 are preferred in modern security for memory-hardness against GPU and ASIC cracking. 
				GPU Cracking - Modern GPUs contain thousands of parallel processing cores that can test billions of password combinations per second. Definitely important to defend against in the modern age. If each password guess requires 64 megabytes of RAM, a GPU with 8 gigabytes of memory can only test about 125 passwords at once instead of thousands. 
				Application-Specific Integrated Circuit (ASIC) Cracking - Custom built microchips that most likely only do one thing: run bcrypt math at insanely fast speeds. RAM takes up a lot of physical space on a microchip and also uses a lot of electricity. Forcing an ASIC to include large amounts of fast memory makes custom hardware way too expensive to build, often discouraging an attacker. 
		 
		