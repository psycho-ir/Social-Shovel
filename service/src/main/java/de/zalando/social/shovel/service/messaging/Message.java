package de.zalando.social.shovel.service.messaging;

import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

/**
 * Created by SOROOSH on 4/19/15.
 */
public class Message implements Serializable {

    public String getCountry() {
        return country;
    }

    public enum UserOpinion {
        SATISFIED,
        NEUTRAL,
        UNSATISFIED

    }

    public static class MessageBuilder {
        private final Message msg;

        public MessageBuilder(String content, String provider, String... topics) {
            ArrayList<String> selectedTopics = new ArrayList<>();
            for (String t : topics) {
                String normalized = content.toLowerCase();
                if (normalized.contains(t.toLowerCase())) {
                    selectedTopics.add(t);
                }
            }
            this.msg = new Message(content, provider,  selectedTopics.toArray(new String[]{}));
        }

        public MessageBuilder withLang(String lang) {
            this.msg.language = lang;
            return this;
        }

        public MessageBuilder withUserInfo(String userId, String name, String lastName, String imageUrl) {
            UserInfo userInfo = new UserInfo(userId, name, lastName, imageUrl);
            this.msg.userInfo = userInfo;
            return this;

        }

        public MessageBuilder on(Date date) {
            this.msg.postedDate = date;
            return this;
        }

        public MessageBuilder at(String country){
            this.msg.country = country;
            return this;
        }

        public Message build() {
            return this.msg;
        }
    }

    @Id
    private String id;

    private final String[] topics;
    private final String provider;
    private final String content;
    private String country;
    private String language;
    private UserInfo userInfo;
    private Message.UserOpinion userOpinion;



    private String messageClass;
    private Date postedDate;

    private Message(){

        topics = new String[0];
        provider = null;
        content = null;
    }
    private Message(String content, String provider, String... topics) {
        this.topics = topics;
        this.content = content;
        this.provider = provider;
        this.userOpinion = null;
        this.messageClass = null;
        this.id= UUID.randomUUID().toString();
    }

    public Message(String content, String language, UserInfo userInfo, String provider, Date postedDate, String... topics) {
        this.topics = topics;
        this.content = content;
        this.language = language;
        this.userInfo = userInfo;
        this.provider = provider;
        this.postedDate = postedDate;
        this.userOpinion = null;
        this.messageClass = null;
        this.id= UUID.randomUUID().toString();
    }

    public void changeUserOpinion(Message.UserOpinion opinion) {
        if (this.userOpinion != null && this.userOpinion != UserOpinion.NEUTRAL) {
            throw new IllegalStateException("It is not possible to change the opinion more than 1 time!");
        }
        this.userOpinion = opinion;
    }

    public void changeClass(String messageClass) {
        this.messageClass = messageClass;

    }

    public Date getPostedDate() {
        return postedDate;
    }

    public String getProvider() {
        return provider;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public String getLanguage() {
        return language;
    }

    public String getContent() {
        return content;
    }


    public String[] getTopics() {
        return topics;
    }

    public String getMessageClass() {
        return messageClass;
    }

    public UserOpinion getUserOpinion() {
        return userOpinion;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Message{" +
                "topics=" + Arrays.toString(topics) +
                ", provider='" + provider + '\'' +
                ", content='" + content + '\'' +
                ", language='" + language + '\'' +
                ", userInfo=" + userInfo +
                ", postedDate=" + postedDate +
                '}';
    }
}
