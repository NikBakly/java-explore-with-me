package ru.yandex.main.compilation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.main.event.Event;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "compilation")
@Getter
@Setter
@ToString
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id")
    List<Event> events = new ArrayList<>();

    Boolean pinned;

    String title;
}
