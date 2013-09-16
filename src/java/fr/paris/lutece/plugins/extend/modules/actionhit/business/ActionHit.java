package fr.paris.lutece.plugins.extend.modules.actionhit.business;

/**
 * Class that count the number of times an action is performed over a resource
 */
public class ActionHit
{
    /**
     * ActionHit resource type
     */
    public static final String RESOURCE_TYPE = "ACTION_HIT";

    private int _nIdActionHit;
    private String _strActionName;
    private String _strExtendableResourceType;
    private String _strIdExtendableResource;
    private int _nHit;

    /**
     * Get the id of the action hit
     * @return The id of the action hit
     */
    public int getIdActionHit( )
    {
        return _nIdActionHit;
    }

    /**
     * Set the id of the action hit
     * @param nIdActionHit The id of the action hit
     */
    public void setIdActionHit( int nIdActionHit )
    {
        this._nIdActionHit = nIdActionHit;
    }

    /**
     * Default constructor
     */
    public ActionHit( )
    {

    }

    /**
     * Creates an action hit
     * @param strActionName The name of the action
     * @param strIdExtendableResource The id of the extendable resource
     * @param strExtendableResourceType The type of the extendable resource
     * @param nHit The number of hits
     */
    public ActionHit( String strActionName, String strIdExtendableResource, String strExtendableResourceType, int nHit )
    {
        this._strActionName = strActionName;
        this._strIdExtendableResource = strIdExtendableResource;
        this._strExtendableResourceType = strExtendableResourceType;
        this._nHit = nHit;
    }

    /**
     * Get the name of the action
     * @return The name of the action
     */
    public String getActionName( )
    {
        return _strActionName;
    }

    /**
     * Set the name of the action
     * @param strActionName The name of the action
     */
    public void setActionName( String strActionName )
    {
        this._strActionName = strActionName;
    }

    /**
     * Get the resource type associated with this action hit
     * @return The resource type associated with this action hit
     */
    public String getExtendableResourceType( )
    {
        return _strExtendableResourceType;
    }

    /**
     * Set the resource type associated with this action hit
     * @param strExtendableResourceType The resource type associated with this
     *            action hit
     */
    public void setExtendableResourceType( String strExtendableResourceType )
    {
        this._strExtendableResourceType = strExtendableResourceType;
    }

    /**
     * Get the id of the extendable resource
     * @return The id of the extendable resource
     */
    public String getIdExtendableResource( )
    {
        return _strIdExtendableResource;
    }

    /**
     * Set the id of the extendable resource
     * @param strIdExtendableResource The id of the extendable resource
     */
    public void setIdExtendableResource( String strIdExtendableResource )
    {
        this._strIdExtendableResource = strIdExtendableResource;
    }

    /**
     * Get the number of hits
     * @return The number of hits
     */
    public int getHit( )
    {
        return _nHit;
    }

    /**
     * Set the number of hits
     * @param nHit The number of hits
     */
    public void setHit( int nHit )
    {
        this._nHit = nHit;
    }
}
