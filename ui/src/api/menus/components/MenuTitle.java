package api.menus.components;

/**
 * The title of a Menu. Each Menu has its own MenuTitle.
 * Contains a String which acts as the Menu's name as well.
 */
public class MenuTitle {

    private String titleName;
    public MenuTitle(String title) {
        this.titleName = title;
    }

    /**
     * Prints to the screen the titleName in title format.
     * Note: this is not show() inherited from class Menu.
     */
    public void show() {
        System.out.print(titleHorizontalLine() + titleContents() + titleHorizontalLine());
    }

    /**
     * Builds a String containing MENU_WIDTH characters of TITLE_HLINE chars. The top and bottom parts of the title.
     * @return A String containing a line of width MENU_WIDTH using TITLE_HLINE chars.
     */
    private String titleHorizontalLine() {
        StringBuilder builder = new StringBuilder(Constants.MENU_WIDTH);
        builder.append(Constants.TITLE_EDGE);
        for (int i = 0; i < Constants.MENU_WIDTH - 2; i ++) {
            builder.append(Constants.TITLE_HLINE);
        }
        builder.append(Constants.TITLE_EDGE);
        builder.append("\r\n");
        return builder.toString();
    }

    /**
     * Builds the middle line of the title, including titleName.
     * @return A String containing MENU_WIDTH characters of TITLE_FILL characters with the titleName overwritten in the
     *         center.
     */
    private String titleContents() {
        StringBuilder builder = new StringBuilder();

        builder.append(Constants.TITLE_VLINE);
        for (int i = 0; i < (Constants.MENU_WIDTH - 2 - titleName.length()) / 2; i++) {
            builder.append(Constants.TITLE_FILL);
        }
        builder.append(titleName);
        for (int i = 0; i < (Constants.MENU_WIDTH - 2 - titleName.length()) / 2; i++) {
            builder.append(Constants.TITLE_FILL);
        }
        builder.append(Constants.TITLE_VLINE);
        builder.append("\r\n");

        return builder.toString();
    }

    @Override
    public String toString() {
        return titleName;
    }

    public String getTitleName() {
        return titleName;
    }
}