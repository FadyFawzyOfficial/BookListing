package com.engineerfadyfawzi.booklisting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.Preference;
import android.preference.PreferenceManager;

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
            
            // Update the preference summary when the settings activity is launched.
            
            // Use PreferenceFragment's findPreference() method to get the Preference object and,
            Preference maxResults = findPreference( getString( R.string.settings_max_results_key ) );
            // Setup the preference using this helper method.
            bindPreferenceSummaryToValue( maxResults );
            
            Preference orderBy = findPreference( getString( R.string.settings_order_by_key ) );
            bindPreferenceSummaryToValue( orderBy );
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
            String newValueString = newValue.toString();
            
            // If preference is ListPreference
            if ( preference instanceof ListPreference )
            {
                // Update the summary fo a ListPreference( using the label, instead of the key).
                ListPreference listPreference = ( ListPreference ) preference;
                int prefIndex = listPreference.findIndexOfValue( newValueString );
                
                if ( prefIndex >= 0 )
                {
                    CharSequence[] labels = listPreference.getEntries();
                    preference.setSummary( labels[ prefIndex ] );
                }
            }
            else
                // Update the summary of preference using it's key value
                preference.setSummary( newValueString );
            
            return true;
        }
        
        /**
         * Set the current BookPreferenceFragment instance as teh listener on each preference.
         * We also read the current value of the preference stored in the SharedPreference on
         * the device, and display that in the preference summary
         * (so that the user can see the current value of the preference).ء
         *
         * @param preference that will show or update it's value
         */
        private void bindPreferenceSummaryToValue( Preference preference )
        {
            // Sets the callback to be invoked when this Preference is changed by the user
            // (but before the internal state has been changed).
            preference.setOnPreferenceChangeListener( this );
            
            SharedPreferences sharedPreferences =
                    PreferenceManager.getDefaultSharedPreferences( preference.getContext() );
            
            // getString: returns the preference value if it exists, or default value which is the
            // second argument of this method (Value to return if this preference does not exist).
            String preferenceString = sharedPreferences.getString( preference.getKey(), "" );
            // To display the preference summary on Activity launched.
            onPreferenceChange( preference, preferenceString );
        }
    }
}