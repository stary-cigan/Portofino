/*
* Copyright (C) 2005-2016 ManyDesigns srl.  All rights reserved.
* http://www.manydesigns.com/
*
* Unless you have purchased a commercial license agreement from ManyDesigns srl,
* the following license terms apply:
*
* This program is free software; you can redistribute it and/or modify
* it under the terms of the GNU General Public License version 3 as published by
* the Free Software Foundation.
*
* There are special exceptions to the terms and conditions of the GPL
* as it is applied to this software. View the full text of the
* exception in file OPEN-SOURCE-LICENSE.txt in the directory of this
* software distribution.
*
* This program is distributed WITHOUT ANY WARRANTY; and without the
* implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
* See the GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, see http://www.gnu.org/licenses/gpl.txt
* or write to:
* Free Software Foundation, Inc.,
* 59 Temple Place - Suite 330,
* Boston, MA  02111-1307  USA
*
*/

package com.manydesigns.portofino.menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Paolo Predonzani     - paolo.predonzani@manydesigns.com
 * @author Angelo Lupo          - angelo.lupo@manydesigns.com
 * @author Giampiero Granatella - giampiero.granatella@manydesigns.com
 * @author Alessio Stalla       - alessio.stalla@manydesigns.com
 */
public class MenuBuilder {
    public static final String copyright =
            "Copyright (C) 2005-2016, ManyDesigns srl";

    public final List<MenuAppender> menuAppenders = new ArrayList<MenuAppender>();

    public Menu build() {
        Menu menu = new Menu();
        for(MenuAppender appender : menuAppenders) {
            appender.append(menu);
        }
        Collections.sort(menu.items, new MenuItemComparator());
        for(MenuItem menuItem : menu.items) {
            if(menuItem instanceof MenuGroup) {
                Collections.sort(((MenuGroup) menuItem).menuLinks, new MenuItemComparator());
            }
        }
        return menu;
    }

    public List<MenuAppender> getMenuAppenders() {
        return menuAppenders;
    }

    public static class MenuItemComparator implements Comparator<MenuItem> {
        @Override
        public int compare(MenuItem mi1, MenuItem mi2) {
            return Double.compare(mi1.order, mi2.order);
        }
    }
}
