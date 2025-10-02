# @shannic/audio-client

Audio client capacitor plugin.

## Install

```bash
npm install @shannic/audio-client
npx cap sync
```

## API

<docgen-index>

* [`get(...)`](#get)
* [`search(...)`](#search)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### get(...)

```typescript
get(options: { id: string; }) => Promise<Audio>
```

| Param         | Type                         |
| ------------- | ---------------------------- |
| **`options`** | <code>{ id: string; }</code> |

**Returns:** <code>Promise&lt;<a href="#audio">Audio</a>&gt;</code>

--------------------


### search(...)

```typescript
search(options: { query: string; next_token?: string | null; }) => Promise<Search>
```

| Param         | Type                                                         |
| ------------- | ------------------------------------------------------------ |
| **`options`** | <code>{ query: string; next_token?: string \| null; }</code> |

**Returns:** <code>Promise&lt;<a href="#search">Search</a>&gt;</code>

--------------------


### Interfaces


#### Audio

| Prop             | Type                     |
| ---------------- | ------------------------ |
| **`id`**         | <code>string</code>      |
| **`title`**      | <code>string</code>      |
| **`author`**     | <code>string</code>      |
| **`thumbnails`** | <code>Thumbnail[]</code> |
| **`duration`**   | <code>number</code>      |
| **`url`**        | <code>string</code>      |


#### Thumbnail

| Prop         | Type                |
| ------------ | ------------------- |
| **`url`**    | <code>string</code> |
| **`height`** | <code>number</code> |
| **`width`**  | <code>number</code> |


#### Search

| Prop             | Type                                                                                                     |
| ---------------- | -------------------------------------------------------------------------------------------------------- |
| **`next_token`** | <code>string \| null</code>                                                                              |
| **`results`**    | <code>{ id: string; title: string; author: string; thumbnails: Thumbnail[]; duration: string; }[]</code> |

</docgen-api>
