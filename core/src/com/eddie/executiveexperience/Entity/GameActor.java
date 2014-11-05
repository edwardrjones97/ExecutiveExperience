package com.eddie.executiveexperience.Entity;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Logger;
import com.eddie.executiveexperience.Entity.UserData.EntityUserData;
import com.eddie.executiveexperience.Entity.UserData.UserData;
import com.eddie.executiveexperience.Env;
import com.eddie.executiveexperience.GameStage;
import com.eddie.executiveexperience.Scripting.JythonScript;

public abstract class GameActor extends Actor
{
    protected static final Logger logger = new Logger("GameActor", Env.debugLevel);

    protected Body body;
    protected UserData userData;

    public GameActor(GameStage gameStage)
    {
        gameStage.addActor(this);
    }

    @Override
    public void act(float delta)
    {
        if (getUserData() instanceof EntityUserData)
        {
            EntityUserData entityUserData = (EntityUserData) getUserData();

            JythonScript script = entityUserData.getEntityData().getScript();

            if (script != null)
            {
                script.executeFunction("act", this);
            }
        }

        super.act(delta);
    }

    public Body getBody()
    {
        return body;
    }

    public void setBody(Body body)
    {
        this.body = body;

        this.userData = (UserData) body.getUserData();
    }

    public abstract UserData getUserData();
}
