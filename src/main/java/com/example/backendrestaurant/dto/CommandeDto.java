package com.example.backendrestaurant.dto;

import com.example.backendrestaurant.models.CommandeStatus;

import java.util.Date;

public class CommandeDto {
    private Long id;
    private Date datedecommande;
    private CommandeStatus commandeStatus;
    private Long user;
    private String userName;
    private Long menuItemId;
    private String menuItemName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDatedecommande() {
        return datedecommande;
    }

    public void setDatedecommande(Date datedecommande) {
        this.datedecommande = datedecommande;
    }

    public CommandeStatus getCommandeStatus() {
        return commandeStatus;
    }

    public void setCommandeStatus(CommandeStatus commandeStatus) {
        this.commandeStatus = commandeStatus;
    }

    public Long getUserId() {
        return user;
    }

    public void setUserId(Long userId) {
        this.user = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(Long menuItemId) {
        this.menuItemId = menuItemId;
    }

    public String getMenuItemName() {
        return menuItemName;
    }

    public void setMenuItemName(String menuItemName) {
        this.menuItemName = menuItemName;
    }
    @Override
    public String toString() {
        return "Commande [id=" + id + ", datedecommande=" + datedecommande + ", commandeStatus=" + commandeStatus + ", user=" + user + ", menuItemId="
                + menuItemId + "]";
    }

}
