package edu.br.usp.each.si.fsi.ultimatejetfighter;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import edu.br.usp.each.si.fsi.ultimate.UltimateJetFighter;


public class UltimateJetFighterActivity extends AndroidApplication {
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false;
		config.useCompass = false;
		config.useWakelock = true;
		//config.useGLSurfaceView20API18 = true;
		initialize(new UltimateJetFighter(), config);
    }
}
