package app.models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class SysRoleMenu extends Model {

    @Id
    Long id;

    @ManyToOne
    SysRole role;

    @ManyToOne
    SysMenu menu;

    public static Finder<Long, SysRoleMenu> find = new Finder<Long, SysRoleMenu>(SysRoleMenu.class);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SysRole getRole() {
        return role;
    }

    public void setRole(SysRole role) {
        this.role = role;
    }

    public SysMenu getMenu() {
        return menu;
    }

    public void setMenu(SysMenu menu) {
        this.menu = menu;
    }
}
