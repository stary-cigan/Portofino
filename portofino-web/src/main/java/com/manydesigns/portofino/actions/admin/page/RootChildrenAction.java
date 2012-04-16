/*
 * Copyright (C) 2005-2012 ManyDesigns srl.  All rights reserved.
 * http://www.manydesigns.com/
 *
 * Unless you have purchased a commercial license agreement from ManyDesigns srl,
 * the following license terms apply:
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.manydesigns.portofino.actions.admin.page;

import com.manydesigns.portofino.buttons.annotations.Button;
import com.manydesigns.portofino.security.RequiresAdministrator;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import java.io.File;

/**
 * @author Paolo Predonzani     - paolo.predonzani@manydesigns.com
 * @author Angelo Lupo          - angelo.lupo@manydesigns.com
 * @author Giampiero Granatella - giampiero.granatella@manydesigns.com
 * @author Alessio Stalla       - alessio.stalla@manydesigns.com
 */
@UrlBinding("/actions/admin/root-page/children")
public class RootChildrenAction extends RootConfigurationAction {
    public static final String copyright =
            "Copyright (c) 2005-2012, ManyDesigns srl";

    @Override
    @DefaultHandler
    @RequiresAdministrator
    public Resolution pageChildren() {
        return super.pageChildren();
    }

    @Override
    protected Resolution forwardToPageChildren() {
        return new ForwardResolution("/layouts/admin/rootChildren.jsp");
    }

    @Override
    @Button(list = "root-children", key = "commons.update", order = 1)
    public Resolution updatePageChildren() {
        return super.updatePageChildren();
    }

    @Override
    protected void setupChildPages() {
        File directory = application.getPagesDir();
        childPagesForm = setupChildPagesForm(childPages, directory, getPage().getLayout(), "");
    }

    @Override
    protected String[] getChildPagesFormFields() {
        return new String[] { "active", "name", "title", "showInNavigation", };
    }

    public String getActionPath() {
        return "/actions/admin/root-page/children";
    }

}