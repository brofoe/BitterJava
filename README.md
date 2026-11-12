# BitterJava

> One-line description: an intentionally vulnerable forum built with Spring Boot for learning web security.

![Build](badge) ![License](badge) ![Java](badge) ![Status](badge)

> ⚠️ **Warning:** This application is intentionally vulnerable. Run it only on your own machine, bound to localhost. Never deploy it to a public server.

[screenshot or short demo GIF]

## Table of Contents
- [About](#about)
- [Who This Is For](#who-this-is-for)
- [Vulnerabilities at a Glance](#vulnerabilities-at-a-glance)
- [Quick Start](#quick-start)
- [Project Structure](#project-structure)
- [Tech Stack](#tech-stack)
- [How to Use This Project](#how-to-use-this-project)
- [Documentation](#documentation)
- [Roadmap](#roadmap)
- [What I Learned](#what-i-learned)
- [Responsible Use](#responsible-use)
- [License](#license)

## About
- What it is: A shell of a forum website that serves to host vulnerabilities for exploitation practice, exposure, and education. BitterJava includes simple features that are very common in regards to websites and applications, such as: users, logins, comments, searching, and profiles. These features are here to explore the ways in which vulnerabilities come about, how they are executed, why they are critical, and what we can do to mitigate these vulnerabilities. The exploits that can be carried out include but are not limited to: SQL Injection, Stored XSS, Broken Access Control, Weak Authentication. 
- Why I built it: I find it cumbersome to get hands-on practice in Cybersecurity. I thought of BitterJava by wanting a more simple approach to exploits, vulnerabilities, and Cybersecurity practices as a whole. My goal for BitterJava is to make it a hub for learning vulnerabilities and exploits. I want it to be similar to other educational tools such as TryHackMe where you can get the information you need, an environment to work in safely, and a goal to accomplish. 
- What makes it different: The difference between BitterJava and other purposely vulnerable applications is that it has very little dependencies (Spring Web, Thymeleaf, JDBC, and an H2 Database) so there is nothing to install. The application is also very tiny, allowing it to run properly on the majority of machines. In addition to providing a safe environment to practice exploitations, BitterJava also teaches and educates in regards to the vulnerabilities including how it occured, in which ways vulnerabilities be executed, and how can security practicioners mitigate the chances of successful exploitation. 

## Who This Is For
- Learners:
- Students:
- Recruiters / engineers reviewing this repo:

## Vulnerabilities at a Glance

| # | Vulnerability | Where in the app | CWE | OWASP Top 10 | Difficulty | Status | Write-up |
|---|---|---|---|---|---|---|---|
| 1 | SQL Injection | Search box | CWE-89 | #5 (Injection) 🟠 | | Planned | [link] |
| 2 | Stored XSS | Posts / comments | CWE-79 | #5 (Injection) 🟠 | | Planned | [link] |
| 3 | Broken Access Control (IDOR) | Edit/delete post, profiles | CWE-639 | #1 (Broken Access Control) 🔴 | | Planned | [link] |
| 4 | Weak Authentication | Register / login | CWE-916 | #7 (Authentication Failures) 🟡 | | Planned | [link] |

## Quick Start
### Prerequisites
### Run locally
### Run with Docker (coming soon)
### Default test accounts
### Verify it works

## Project Structure
[short annotated tree]

## Tech Stack
| Layer | Technology | Why |
|---|---|---|

## How to Use This Project
### As a learner (try it yourself first)
### As a reviewer (see the finished write-ups)
### Branches and tags
- Baseline:
- Vulnerable:
- Hardened:

## Documentation
- [Architecture](docs/architecture.md)
- [Threat Model](docs/threat-model.md)
- [Vulnerability Write-ups](docs/vulnerabilities/)
- [Learnings Log](docs/LEARNINGS.md)

## Roadmap
[short list, link to ROADMAP.md]

## What I Learned
[3-5 bullet summary, link to LEARNINGS.md]

## Responsible Use
[short statement, link to SECURITY.md]

## License
MIT, see [LICENSE](LICENSE).
