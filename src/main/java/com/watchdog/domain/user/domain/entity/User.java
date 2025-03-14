package com.watchdog.domain.user.domain.entity;

import com.watchdog.common.base.BaseEntity;
import com.watchdog.domain.user.domain.status.Status;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "provider_id", nullable = false)
    private String providerId;

    @Setter
    @Column(name = "nick_name", nullable = false)
    private String nickName;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "tmp_chat")
    private Long tmpChat;

    @Column(name = "tmp_memo")
    private Long tmpMemo;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    private List<ChatRoom> chatRooms;

    public void updateTmpChat(Long tmpChat) {
        this.tmpChat = tmpChat;
    }

    public void deleteTmpChat(){
        this.tmpChat = null;
    }
}