package ru.erfolk.audit;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Eugene Gusev (egusev@gmail.com)
 */
@Entity
@Table(name = "Logs")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @NotNull
    @Column(nullable = false)
    private long last;

    @Column
    private Integer user;

    @NotNull
    @Column(nullable = false)
    private String path;

    @NotNull
    @Column(nullable = false)
    private Integer responseCode;

    public Log(Date date, long last, Integer user, String path, Integer responseCode) {
        this.date = date;
        this.last = last;
        this.user = user;
        this.path = path;
        this.responseCode = responseCode;
    }
}
