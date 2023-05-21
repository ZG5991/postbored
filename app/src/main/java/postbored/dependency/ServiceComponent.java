package postbored.dependency;

import dagger.Component;

import javax.inject.Singleton;

/**
 * Dagger component for providing dependency injection in the Music Playlist Service.
 */
@Singleton
@Component(modules = {DaoModule.class})
public interface ServiceComponent {

//    /**
//     * Provides the relevant activity.
//     * @return AddSongToPlaylistActivity
//     */
//    AddSongToPlaylistActivity provideAddSongToPlaylistActivity();
//
//    /**
//     * Provides the relevant activity.
//     * @return CreatePlaylistActivity
//     */
//    CreatePlaylistActivity provideCreatePlaylistActivity();
//
//    /**
//     * Provides the relevant activity.
//     * @return GetPlaylistActivity
//     */
//    GetPlaylistActivity provideGetPlaylistActivity();
//
//    /**
//     * Provides the relevant activity.
//     * @return GetPlaylistActivity
//     */
//    SearchPlaylistsActivity provideSearchPlaylistsActivity();
//
//    /**
//     * Provides the relevant activity.
//     * @return GetPlaylistSongsActivity
//     */
//    GetPlaylistSongsActivity provideGetPlaylistSongsActivity();
//
//    /**
//     * Provides the relevant activity.
//     * @return UpdatePlaylistActivity
//     */
//    UpdatePlaylistActivity provideUpdatePlaylistActivity();

}
