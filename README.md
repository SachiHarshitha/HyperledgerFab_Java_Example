# IMPORTANT

Windows extras
On Windows 10 you should use the native Docker distribution and you may use the Windows PowerShell. However, for the binaries command to succeed you will still need to have the uname command available. You can get it as part of Git but beware that only the 64bit version is supported.

Before running any git clone commands, run the following commands:

git config --global core.autocrlf false
git config --global core.longpaths true
You can check the setting of these parameters with the following commands:

git config --get core.autocrlf
git config --get core.longpaths
These need to be false and true respectively.

