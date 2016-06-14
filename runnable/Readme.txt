1.You have to run server first, then run client.
2.The client number is set to only one player. So you only need to run one client. If you 
  would like to have more player, you have to modified the original code. Change variable 
  playerNum in Server->GameThread.
3.After you click start, the program will need to load image. The window will have 
  completely nothing. These process may up to 30sec or longer. Please be patient.
4.Sometimes an error may occur when running client. If the starting screen doesn't show up,
  try to run the client again. Usually this will solve the problem.
5.If you cannot run the program, try add virtual machine arguments in original code. Right 
  click on the project->Run as->Run Configurations->java Application. On both Main, go to 
  tag "Argument" and add "-Xmx2048m" to VM arguments.