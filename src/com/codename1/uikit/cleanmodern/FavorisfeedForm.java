package com.codename1.uikit.cleanmodern;

import bmg.crud.FavorisCrud;
import bmg.crud.UserCo;
import bmg.crud.UserCrud;
import bmg.entities.Favoris;
import bmg.entities.User;
import com.codename1.components.ImageViewer;
import com.codename1.components.InteractionDialog;
import com.codename1.components.MultiButton;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.contacts.Contact;
import com.codename1.contacts.ContactsManager;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.events.DataChangedListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;

public class FavorisfeedForm extends BaseForm {

    Button sendMsg, seeProfile, delete;
    Resources r;

    public FavorisfeedForm(Resources res) {
        super("Newsfeed", BoxLayout.y());

        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);

        ConnectionRequest connectionRequest = new ConnectionRequest();
        connectionRequest.setUrl("http://localhost/Codenameone/selectF.php?id_user='" + UserCo.userCo.getId_u()+"'");
        NetworkManager.getInstance().addToQueue(connectionRequest);

        tb.addMaterialCommandToRightBar("", FontImage.MATERIAL_ADD, e -> {
            if (Dialog.show("Adding Favourite", "This will add your potentiel contact to your favourite list", "OK", "Cancel")) {
                searchInContact();
                ToastBar.showMessage("Succees, Please reload the page", FontImage.MATERIAL_CHECK_CIRCLE);
            }
        });

        connectionRequest.addResponseListener(new ActionListener() {

            public void actionPerformed(ActionEvent ev) {

                Tabs swipe = new Tabs();

                Label spacer1 = new Label();
                Label spacer2 = new Label();
                addTab(swipe, res.getImage("rent.jpg"), spacer1, "", "You can see all your favourites ");
                addTab(swipe, res.getImage("dog.jpg"), spacer2, "", " + Searchs in your contact list and adds them to your favourite");

                swipe.setUIID("Container");
                swipe.getContentPane().setUIID("Container");
                swipe.hideTabs();

                ButtonGroup bg = new ButtonGroup();
                int size = Display.getInstance().convertToPixels(1);
                Image unselectedWalkthru = Image.createImage(size, size, 0);
                Graphics g = unselectedWalkthru.getGraphics();
                g.setColor(0xffffff);
                g.setAlpha(100);
                g.setAntiAliased(true);
                g.fillArc(0, 0, size, size, 0, 360);
                Image selectedWalkthru = Image.createImage(size, size, 0);
                g = selectedWalkthru.getGraphics();
                g.setColor(0xffffff);
                g.setAntiAliased(true);
                g.fillArc(0, 0, size, size, 0, 360);
                RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
                FlowLayout flow = new FlowLayout(CENTER);
                flow.setValign(BOTTOM);
                Container radioContainer = new Container(flow);
                for (int iter = 0; iter < rbs.length; iter++) {
                    rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
                    rbs[iter].setPressedIcon(selectedWalkthru);
                    rbs[iter].setUIID("Label");
                    radioContainer.add(rbs[iter]);
                }

                rbs[0].setSelected(true);
                swipe.addSelectionListener((i, ii) -> {
                    if (!rbs[ii].isSelected()) {
                        rbs[ii].setSelected(true);
                    }
                });

                Component.setSameSize(radioContainer, spacer1, spacer2);
                add(LayeredLayout.encloseIn(swipe, radioContainer));

                FavorisCrud fc = new FavorisCrud();
               
                 EncodedImage placeholder = EncodedImage.createFromImage(res.getImage("Logo.png"),true);
                 
                try {

                    for (Favoris f : fc.getListT(new String(connectionRequest.getResponseData()))) {

                        addButton2(res.getImage("user.png"), f.getFirstname() + " " + f.getLastname(), "Alias : " + f.getAlias(), sendMsg = new Button("Messages"), seeProfile = new Button("See Profile"), delete = new Button("X"), f.getIdu());

                    }
                } catch (Exception x) {
                }

                tb.addMaterialCommandToRightBar("", FontImage.MATERIAL_SEARCH, e -> {

                    Button ok = new Button("ok");
                    final Button showPopup = new Button("Show Popup");
                    Dialog d = new Dialog("Search in your list");
                    TextField tf = new TextField("", "Search..");
                    tf.setUIID("PopupBody");
                    d.setLayout(new BorderLayout());
                    d.add(BorderLayout.CENTER, tf);
                    d.add(BorderLayout.SOUTH, ok);

                    ok.addActionListener(ex -> {
                        try {

                            for (Favoris f : fc.getListT(new String(connectionRequest.getResponseData()))) {
                                if (f.getLastname().equalsIgnoreCase(tf.getText()) || f.getFirstname().equalsIgnoreCase(tf.getText()) || f.getAlias().equalsIgnoreCase(tf.getText())) {
                                    removeAll();
                                    addButton2(res.getImage("news-item-1.jpg"), f.getFirstname() + " " + f.getLastname(), "Alias : " + f.getAlias(), sendMsg = new Button("Messages"), seeProfile = new Button("See Profile"), delete = new Button("X"), f.getIdu());
                                }
                            }
                        } catch (Exception x) {
                        }

                        d.dispose();
                    });
                    d.showPopupDialog(showPopup);

                });
            }
        });

    }

    private void addTab(Tabs swipe, Image img, Label spacer, String commentsStr, String text) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        if (img.getHeight() < size) {
            img = img.scaledHeight(size);
        }

        Label comments = new Label(commentsStr);
        FontImage.setMaterialIcon(comments, FontImage.MATERIAL_CHAT);
        if (img.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }
        ScaleImageLabel image = new ScaleImageLabel(img);
        image.setUIID("Container");
        image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        Label overlay = new Label(" ", "ImageOverlay");

        Container page1
                = LayeredLayout.encloseIn(
                        image,
                        overlay,
                        BorderLayout.south(
                                BoxLayout.encloseY(
                                        new SpanLabel(text, "LargeWhiteText"),
                                        FlowLayout.encloseIn(comments),
                                        spacer
                                )
                        )
                );

        swipe.addTab("", page1);
    }

    private void addButton(Image img, String title, boolean liked, int likeCount, int commentCount, int idu) {
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        Button image = new Button(img.fill(width, height));
        image.setUIID("Label");
        Container cnt = BorderLayout.west(image);
        cnt.setLeadComponent(image);
        TextArea ta = new TextArea(title);
        ta.setUIID("NewsTopLine");
        ta.setEditable(false);

        Label likes = new Label(likeCount + " Likes  ", "NewsBottomLine");
        likes.setTextPosition(RIGHT);
        if (!liked) {
            FontImage.setMaterialIcon(likes, FontImage.MATERIAL_FAVORITE);
        } else {
            Style s = new Style(likes.getUnselectedStyle());
            s.setFgColor(0xff2d55);
            FontImage heartImage = FontImage.createMaterial(FontImage.MATERIAL_FAVORITE, s);
            likes.setIcon(heartImage);
        }
        Label comments = new Label(commentCount + " Comments", "NewsBottomLine");
        FontImage.setMaterialIcon(likes, FontImage.MATERIAL_CHAT);

        cnt.add(BorderLayout.CENTER,
                BoxLayout.encloseY(
                        ta,
                        BoxLayout.encloseX(likes, comments)
                ));
        add(cnt);
        image.addActionListener(e -> new MsgForm(idu));
    }

    private void searchInContact() {
        Contact c1 = new Contact();
        c1.setPrimaryPhoneNumber("45");
        ContactsManager.createContact("Dorra", "Dalhoumi", "52326060", "1235", "9898", "DorraD@es.tn");

        ContactsManager.createContact("Daly", "Sedrine", "52326061", "1245", "7777", "Dalyy@es.tn");
        Contact[] contacts = Display.getInstance().getAllContacts(true, true, true, true, true, true);
        UserCrud uc = new UserCrud();
        ConnectionRequest cnx = new ConnectionRequest();

        cnx.setUrl("http://localhost/Codenameone/userCo.php?");

        NetworkManager.getInstance().addToQueue(cnx);
        cnx.addResponseListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ev) {
                for (User u : uc.getUserCo(new String(cnx.getResponseData()))) {
                    for (Contact c : contacts) {
                        if (u.getNumtel().equals(c.getPrimaryPhoneNumber())) {
                            ConnectionRequest cnxInsert = new ConnectionRequest();
                            cnxInsert.setUrl("http://localhost/codenameone/insertFav?user='" + UserCo.userCo.getId_u() + "'&userFav='" + u.getId_u() + "'&alias='" + c.getDisplayName() + "'");
                            NetworkManager.getInstance().addToQueue(cnxInsert);
                        }
                    }
                }
            }

        });

    }

    private void addButton2(Image img, String title, String date, Button accepter, Button refuser, Button delete, int idu) {
        int height = Display.getInstance().convertToPixels(13.5f);
        int width = Display.getInstance().convertToPixels(11f);
        Label image = new Label(img.fill(width, height));

        Label datee = new Label(date);
        image.setUIID("Label");
        datee.setUIID("SideCommand");
        delete.setUIID("FloatingHint");
        Container cnt = BorderLayout.west(image);
        Label spacer1 = new Label();
        cnt.getAllStyles().setMargin(7, 7, 2, 2);

        Label ta = new Label(title);

        Font largeBoldProportionalFont = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_MEDIUM);

        ta.getAllStyles().setFgColor(0xB85A5A);
        ta.getAllStyles().setFont(largeBoldProportionalFont);

        Font smallBoldProportionalFont = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.FACE_MONOSPACE, Font.SIZE_SMALL);

        datee.getAllStyles().setFont(largeBoldProportionalFont);

        refuser.getAllStyles().setFont(smallBoldProportionalFont);
        accepter.getAllStyles().setFont(smallBoldProportionalFont);
        delete.getAllStyles().setFont(smallBoldProportionalFont);
        cnt.add(BorderLayout.CENTER,
                BoxLayout.encloseY(
                        BoxLayout.encloseX(ta, spacer1, delete),
                        datee,
                        BoxLayout.encloseX(accepter, refuser)
                ));
        accepter.addActionListener(e -> new MsgForm(idu));
        delete.addActionListener(ev -> {
            if (Dialog.show("Delete", "Are you sure you want to delete this user from your list ?", "OK", "Cancel")) {
                ConnectionRequest connectionRequestDel = new ConnectionRequest();
                connectionRequestDel.setUrl("http://localhost/Codenameone/deleteF.php?id=" + UserCo.userCo.getId_u() + "&idf=" + idu);
                NetworkManager.getInstance().addToQueue(connectionRequestDel);
                connectionRequestDel.addResponseListener(new ActionListener() {

                    public void actionPerformed(ActionEvent ev) {
                        ToastBar.showMessage("User deleted from your list, Please reload the page", FontImage.MATERIAL_DELETE);
                    }
                });
            }

        });

        cnt.getAllStyles().setBgColor(0xFCF6F8);
        cnt.getAllStyles().setBgTransparency(255);
        add(cnt);
    }
}
