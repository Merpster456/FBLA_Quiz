# FBLA_Quiz

This project is for the Coding & Programming competition in Future Business Leaders of America. This program tracks community service hours of chapter members, and also automatically tracks progress for the Community Service Awards.

## NOTE

**This project is only compatible with MacOS at the moment.**
Dual compatibility will be coming in the near future!

## Prerequisites

Please download and install the latest version of jdk (jdk-15.0.2) the link you will find below.
When you click the link, the download process is very simple, scroll down to where you see MacOS installer, click the link to the right of it to download the developer kit. A window will pop up, accept the license agreement then click the download button. Once downloaded follow the on screen installation instructions.

[JDK Link](https://www.oracle.com/java/technologies/javase-jdk15-downloads.html)

## Getting Started

**Find the download button in green at the top right corner of the page (clone or download). Click this then click the Download ZIP option.**

Once downloaded extract the zip and proceed to Mac Start.

### NOTE

**If you clone this repository using git on the command line make sure to rename the directory from FBLA_Quiz to FBLA-Quiz-master**

### Mac Start

To run the program on a mac is very simple. All you need is to run a few commands on your terminal. 

#### Command Line Start 

> NOTE: This setup used to be much easier however, due to a new mac update a couple additional steps had to be added.

If you are more experienced with command line, all you need to do is: go to the project directory, give the mac_start.command file permission to execute, then run it.

If you are not experienced with command line thats completely fine, just follow these steps.

1. Open spotlight search by either pressing ⌘ + spacebar or clicking the magnifying glass in the top right of your screen.
2. Then procede to type 'terminal' then press enter.
3. Afterwards a small window should pop up and give you the ability to type into it. Once the window is present type the commands below and press enter.
```
path=$(find ~/ -type d -name FBLA_Quiz-master)
```
> Alot of text will be generated on your screen, don't worry this is supposed to happen.

4. When the command finishes (words stop popping onto your screen) type commands below. (Press enter after you finish typing each line)
```
cd $path

sudo chmod u+x mac_start.command
```
>This command will prompt you for your password. Type in the administrator password for the computer and press enter. This command is giving the program the right to run on your computer.

5. Finally start the program with one last command.
```
./mac_start.command
```


## Login

Once you start the application you will be greeted with a choice to login, or continue as guest. If you want to complete the quiz as a guest click the "continue as guest" button, however if you want to log in use the credentials below. 

### Advisor Credentials
ID: advisor

Pass: advisor

### Student Credentials
ID: student

Pass: student

## Built With

* [Intellij](https://www.jetbrains.com/idea/) - The IDE I used
* [Javafx](https://openjfx.io/) - The external library I used
* [Sqlite](https://sqlite.org/index.html) - The document based database I used

## Authors

* **Ryan Quirk** - *Initial work* - [Merpster456](https://github.com/Merpster456)

## Acknowledgments

* [My Former Project](https://github.com/Merpster456/fbla-coding) - Due to Covid-19 my project was unable to be entered in last years competition
* [Previous Winners](https://github.com/fbla-competitive-events/coding-programming) - Drew much of my inspiration
* [Tutorials Point](https://www.tutorialspoint.com/javafx/index.htm) - Help when I did not know something
* **Lastly, my advisors and parents for giving me the best advice!**

