package fr.paris.lutece.plugins.extend.modules.actionhit.service;

import fr.paris.lutece.portal.service.resource.IExtendableResourceRemovalListener;

import javax.inject.Inject;
import javax.inject.Named;


/**
 * Listener to remove action hits when a resource is removed
 */
public class ActionHitExtendableResourceRemovalListener implements IExtendableResourceRemovalListener
{
    public static final String BEAN_NAME = "extend-actionhit.actionHitExtendableResourceRemovalListener";

    @Inject
    @Named( ActionHitService.BEAN_NAME )
    ActionHitService _actionHitService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void doRemoveResourceExtentions( String strExtendableResourceType, String strIdExtendableResource )
    {
        _actionHitService.removeByResource( strExtendableResourceType, strIdExtendableResource );
    }

}
