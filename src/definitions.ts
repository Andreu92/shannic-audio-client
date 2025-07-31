export interface YoutubeDownloaderPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
