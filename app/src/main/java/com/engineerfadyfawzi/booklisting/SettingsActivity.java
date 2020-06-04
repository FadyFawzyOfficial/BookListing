package com.engineerfadyfawzi.booklisting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.Preference;

public class SettingsActivity extends AppCompatActivity
{
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_settings );
    }
    
    /**
     * This class implements the OnPreferenceChangeListener interface to get notified
     * when a preference changes.
     * Then when a single Preference has been changed by the user and is about to be saved,
     * the onPreferenceChange() method will be invoked with the key of the preference
     * that was changed.
     */
    public static class BookPreferenceFragment extends PreferenceFragment
            implements Preference.OnPreferenceChangeListener
    {
        @Override
        public void onCreate( Bundle savedInstanceState )
        {
            super.onCreate( savedInstanceState );
            addPreferencesFromResource( R.xml.settings_main );
            
            // Use PreferenceFragment's findPreference() method to get the Preference object and,
            Preference maxResults = findPreference( getString( R.string.settings_max_results_key ) );
            // Sets the callback to be invoked when this Preference is changed by the user,
            // (but before the internal state has been updated).
            maxResults.setOnPreferenceChangeListener( this );
        }
        
        /**
         * The code in this method takes care of updating the displayed preference summary after
         * it has been changed.
         *
         * Note that this method returns a boolean, which allows us to prevent a proposed preference
         * change by returning false.
         *
         * @param preference that has been changed by user.
         * @param newValue   of the shared preference.
         *
         * @return
         */
        @Override
        public boolean onPreferenceChange( Preference preference, Object newValue )
        {
            preference.setSummary( newValue.toString() );
            return true;
        }
    }
}