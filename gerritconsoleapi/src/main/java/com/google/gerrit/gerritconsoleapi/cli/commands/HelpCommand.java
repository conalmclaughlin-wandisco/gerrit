package com.google.gerrit.gerritconsoleapi.cli.commands;

import com.google.common.base.Strings;
import com.google.gerrit.gerritconsoleapi.cli.processing.CliCommandItemBase;
import com.google.gerrit.gerritconsoleapi.cli.processing.CmdLineParserFactory;
import com.google.gerrit.gerritconsoleapi.exceptions.LogAndExitException;
import com.google.gerrit.sshd.CommandMetaData;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionHandlerFilter;

import java.util.Arrays;
import java.util.List;

@CommandMetaData(name = "help", description = "Application help")
public class HelpCommand extends CliCommandItemBase {

  @Argument(required =false, index = 0, metaVar = "<command>", usage = "Use: 'java -jar consoleapi.jar help <command>'.\n")
  public String helpOnCommand;

  public HelpCommand(){
    super( "help");
  }

  @Override
  public void execute() throws LogAndExitException {
    if (Strings.isNullOrEmpty(helpOnCommand)) {
      // if we have no argument subcommand, then we should output all the help context.
      new MainProgramCommand().displayHelp();

      return;
    }

    // Workaround!
    // This is because args4j does not display subcommand useage /examples
    // at the moment. see https://github.com/kohsuke/args4j/issues/106
    logtrace("Help on command: " + helpOnCommand);

    // get the appropriate command class now and call.
    // TODO Find some way of looking up the command name, and obtaining the command class from annotations
    // so this works for new commands.
    switch (helpOnCommand.toLowerCase()) {
      case "lfs-info":
        System.out.println(helpOnCommand + " command:");
        new LfsInformationCommand().displayHelp(true);
        break;
      case "lfs-content":
        System.out.println(helpOnCommand + " command:");
        new LfsContentCommand().displayHelp(true);
        break;
      case "config":
        System.out.println(helpOnCommand + " command:");
        new ConfigurationCommand().displayHelp(true);
        break;
      case "help":
        System.out.println(helpOnCommand + " command:");
        displayHelp();
        break;
      default:
        throw new LogAndExitException(String.format("Unknown help command: {%s} specified.", helpOnCommand));
    }
  }

  @Override
  public void execute(String... arguments) throws LogAndExitException {
     // both the execute and display help should do the same thing for the help command!
     execute();
  }

  @Override
  public void displayHelp() {
    // Take a newline, and display the help information, and example use.
    System.out.println("");

    System.out.println("**********************************");
    System.out.println("  Gerrit command line Api - Help. ");
    System.out.println("**********************************");

    // print example use, of just required props for each available command.
    System.out.println("");


    // print example use, of just required props for each available subcommand, to do this
    // output the main help for the top level argument
    CmdLineParser localCmdLineParser = CmdLineParserFactory.createCmdLineParser(new MainProgramCommand());
    localCmdLineParser.printUsage(System.out);


    // TODO, make this easier, to get any command in namespace X?  maybe reflection or bindings?
    // Show each command here.
    List<CliCommandItemBase> commandItemArrayList = Arrays.asList(
        new ConfigurationCommand(),
        new LfsContentCommand(),
        new LfsInformationCommand(),
        new HelpCommand());

    for (CliCommandItemBase commandItem : commandItemArrayList) {
      CmdLineParser subCommandParser = new CmdLineParser(commandItem);

      System.err.println(
          String.format("Command Example - %s\n"
                  + "    java -jar console-api.jar %s %s",
              commandItem.getCommandName(), commandItem.getCommandName(), subCommandParser.printExample(OptionHandlerFilter.REQUIRED)));
      System.err.println("");
    }

  }
}
