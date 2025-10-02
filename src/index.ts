import { registerPlugin } from '@capacitor/core';

import type { AudioClientPlugin } from './definitions';

const AudioClient = registerPlugin<AudioClientPlugin>('AudioClient', {
  web: () => import('./web').then((m) => new m.AudioClientWeb()),
});

export * from './definitions';
export { AudioClient };
