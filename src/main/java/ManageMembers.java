import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.GuildController;

public class ManageMembers extends ListenerAdapter {
    private String[] message;
    private VoiceChannel vchannel;
    private Member member;
    private Role role;
    private boolean check;

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent g) {
        message=g.getMessage().getContentRaw().split(" ");
        member=Methods.getMemberName(g,message);
        role=Methods.checkSenderRole(g,"Moderator");
        check=member.getVoiceState().inVoiceChannel();//state.inVoiceChannel();

        //channel=g.getGuild().getVoiceChannels().stream().filter(c->c.getName(message[2])).findAny().orElse(null);
        if (message[0].equalsIgnoreCase("!movemember")){
            if (role==null){
                Methods.log("Nie masz uprawnien to uzycia tej komendy",g);
            }else if(!check){
                Methods.log("Wskazany uzytkownik nie jest aktualnie w zadnym kanale glosowym ",g);
            }else{

                moveMember(message, g);
                Methods.log("Przeniesiono " + message[1] + " do kanalu " + message[2], g);



        }}
    }






    private void moveMember(String[] args, GuildMessageReceivedEvent g){

        vchannel=g.getGuild().getVoiceChannelsByName(args[2],true).get(0);

        g.getGuild().getController().moveVoiceMember(member,vchannel).queue();

    }
    /*public void log(String log_message, GuildMessageReceivedEvent g) {
        g.getChannel().sendMessage(log_message).queue();
}*/
}
