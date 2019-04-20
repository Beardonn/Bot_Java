import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.channel.text.TextChannelCreateEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;




public class ModifyRole extends ListenerAdapter {
    private Member member;
    private Role role;
    private User user;
    private String[] message;
    boolean role_check;
    private Role rolacheck;

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent g){
        message=g.getMessage().getContentRaw().split(" "); //0 komenda 1 kto 2 rola
        //member=g.getGuild().getMembersByName(message[1],true).get(0).getRoles().contains("Moderator");

        if(message[0].equals("!rola")) {
            //if(member.getRoles().contains(g.getGuild().getRolesByName("Moderator",true).get(0))){
            addRole(message, g);
            System.out.println("asdasd");
            log("costam", g);
            //} else {
           //     log("chuj",g);
            //}

        }
        if(message[0].equals("!removerola")){
            removeRole(message,g);
            log("dupa",g);
        }
    }
    public void log(String log_message,GuildMessageReceivedEvent g){
        g.getGuild().getTextChannelById("564114733368606720").sendMessage(log_message).queue();

    }
    public void addRole(String[] args, GuildMessageReceivedEvent g){
        member=g.getGuild().getMembersByName(args[1],true).get(0);
        role= g.getGuild().getRolesByName(args[2],true).get(0);
        //role_check=g.getGuild().getRoles().equals("Moderator");
        g.getGuild().getController().addRolesToMember(member, role).queue();
        /*if (true) {

        } else{
            log("Nie ma takiej roli",g);
            System.out.println("dziala");
        }*/
    }
    public void removeRole(String[] args,GuildMessageReceivedEvent g){
        member=g.getGuild().getMembersByName(args[1],true).get(0);
        role= g.getGuild().getRolesByName(args[2],true).get(0);
        g.getGuild().getController().removeRolesFromMember(member,role).queue();

    }
    public void createRole(String[] args, GuildMessageReceivedEvent g){
        //g.getGuild().getController().createRole().setName(args[0]).setPermissions().

    }


}
