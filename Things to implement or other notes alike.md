
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
