package team.free.freeway.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "notification", uniqueConstraints = @UniqueConstraint(
        columnNames = {"notification_content", "notification_date"}
))
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @Column(name = "notification_summary")
    private String summary;

    @Column(name = "notification_content")
    private String content;

    @Column(name = "notification_date")
    private LocalDateTime dateTime;

    @Builder
    public Notification(String summary, String content, LocalDateTime dateTime) {
        this.summary = summary;
        this.content = content;
        this.dateTime = dateTime;
    }
}
