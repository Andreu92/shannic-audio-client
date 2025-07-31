import { WebPlugin } from '@capacitor/core';

import type { YoutubeDownloaderPlugin } from './definitions';

export class YoutubeDownloaderWeb extends WebPlugin implements YoutubeDownloaderPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
