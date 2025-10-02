interface Thumbnail {
  url: string;
  height: number;
  width: number;
}

export interface Search {
  next_token: string | null;
  results: {
    id: string;
    title: string;
    author: string;
    thumbnails: Thumbnail[];
    duration: string;
  }[];
}

export interface Audio {
  id: string;
  title: string;
  author: string;
  thumbnails: Thumbnail[];
  duration: number;
  url: string;
}

export interface AudioClientPlugin {
  get(options: { id: string }): Promise<Audio>;
  search(options: { query: string; next_token?: string | null }): Promise<Search>;
}
