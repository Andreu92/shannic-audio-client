import { WebPlugin } from '@capacitor/core';

import type { Search, Audio, AudioClientPlugin } from './definitions';

export class AudioClientWeb extends WebPlugin implements AudioClientPlugin {
  async get(options: { id: string }): Promise<Audio> {
    console.log('not implemented...', options);
    throw new Error('getUrl() is not implemented on web.');
  }

  async search(options: { query: string; next_token: string | null }): Promise<Search> {
    console.log('not implemented...', options);
    throw new Error('search() is not implemented on web.');
  }
}
