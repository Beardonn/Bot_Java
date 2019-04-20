import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.channel.text.TextChannelCreateEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.requests.RestAction;

import java.awt.*;
import java.util.*;
import java.time.Instant;
import java.util.List;

public class GuildEvent extends ListenerAdapter {
    private String[] message;
    private String[] channels;
    private String name;
    private User nick;
    private Role rola;
    private Category category;
    private TextChannel deletext;
    private VoiceChannel deletevoice;

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent g){
        message=g.getMessage().getContentRaw().split(" "); //0 komenda 1 kategoria 2 typ 3 nazwa kanalu 4 usuniecie
        nick=g.getMessage().getAuthor();
        //List<net.dv8tion.jda.core.entities.Category> chuj=g.getGuild().getCategories();
        //category.getGuild().getCategoriesByName(message[1], true);
        //category.getGuild().getCategories().contains(g.getGuild().getCategoriesByName(message[1],true));
        category=g.getGuild().getCategories().stream().filter(r->r.getName().equals(message[1])).findFirst().orElse(null);

        if(message[0].equals("!kanal")){
            if (message.length<4 || category==null)
                //bot_log.getGuild().getCategoriesByName("Test",true).
                log("Zla komenda, wpisz !komendy by zobaczyc",g);
                System.out.println("kurwa2");
            }
                if(message[2].equals("tekstowy")&&message.length<5){
                    createChanel(message,g);
                    log("Utworzona kanal "+message[2]+" w kategori "+message[1]+" o nazwie "+message[3],g);
                    embedbuilder(g,message,nick,"Utworzono");
                }else if(message[2].equals("glosowy")&& message.length<5){
                    createChanel(message,g);
                    log("Utworzona kanal "+message[2]+" w kategori "+message[1]+" o nazwie "+message[3],g);
                    embedbuilder(g,message,nick,"Utworzono");
                } if(message[4].equals("delete")) {
                    deleteChannel(message,g);
                    log("Usunieto kanal "+message[2]+" w kategori "+message[1]+" o nazwie "+message[3],g);
                    embedbuilder(g,message,nick,"Usunieto");
                System.out.println("testsadfjjnsg");


            }

        }
    public void createChanel(String[] args,GuildMessageReceivedEvent g){
        category=g.getGuild().getCategoriesByName(args[1],true).get(0);
        if (args[2].equals("tekstowy")){
        category.createTextChannel(args[3]).queue();
        }else if(args[2].equals("glosowy")){
        category.createVoiceChannel(args[3]).queue();

        }

    }
    public void deleteChannel(String[] args,GuildMessageReceivedEvent g){
        if (args[2].equals("tekstowy")){
        deletext = g.getGuild().getTextChannelsByName(args[3],true).get(0);
        deletext.delete().queue();
        }else if(args[2].equals("glosowy")){
            deletevoice = g.getGuild().getVoiceChannelsByName(args[3],true).get(0);
            deletevoice.delete().queue();
        }

    }
    public void log(String log_message,GuildMessageReceivedEvent g){
        g.getChannel().sendMessage(log_message).queue();

    }
    public void embedbuilder(GuildMessageReceivedEvent g, String[] args, User user,String operacja){
        EmbedBuilder builder=new EmbedBuilder();
        builder.setAuthor(user.getName());
        builder.setColor(Color.BLUE);
        builder.setTitle(operacja+" kanal");
        builder.setDescription(operacja+" kanal "+args[2]+" w kategori "+args[1]+" o nazwie "+args[3]);
        builder.setTimestamp(Instant.now());
        g.getGuild().getTextChannelById("564114733368606720").sendMessage(builder.build()).queue();
    }
}
