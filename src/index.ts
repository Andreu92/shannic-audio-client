import { registerPlugin } from '@capacitor/core';

import type { YoutubeDownloaderPlugin } from './definitions';

const YoutubeDownloader = registerPlugin<YoutubeDownloaderPlugin>('YoutubeDownloader', {
  web: () => import('./web').then((m) => new m.YoutubeDownloaderWeb()),
});

export * from './definitions';
export { YoutubeDownloader };
